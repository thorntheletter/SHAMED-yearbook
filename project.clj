(defproject shamed_yearbook "0.1.0-SNAPSHOT"
  :description "Both the SHA亞MED yearbook website and me trying to learn Clojurescript."
  :url "https://thornhub.com/yearbook" ;; temporary
  :license {:name "Eclipse Public License" ;; i dont feel like thinking about this right now, good enough
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.9.1"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.773"]
                 [org.clojure/core.async  "0.4.500"]
                 [reagent "1.0.0-alpha2"]]

  :plugins [[lein-figwheel "0.5.20"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {:on-jsload "shamed_yearbook.core/on-js-reload"
                           :websocket-host "thornhub.org"}
                           ;; :open-urls will pop open your application
                           ;; in the default browser once Figwheel has
                           ;; started and compiled your application.
                           ;; Comment this out once it no longer serves you.
                           ;; :open-urls ["http://localhost:3000/index.html"]}

                :compiler {:main shamed_yearbook.core
                           :target :bundle
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/out/index.js"
                           :output-dir "resources/public/js/compiled/out"
                           :bundle-cmd {:none ["npx" "webpack" "--mode=development"]
                                        :default ["npx" "webpack"]}
                           :source-map-timestamp true
                           :install-deps true
                           :npm-deps {;; :create-react-class "15.7.0"
                                      :react-pageflip "1.0.0"}
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/shamed_yearbook.js"
                           :main shamed_yearbook.core
                           :optimizations :advanced
                           :pretty-print false
                           :install-deps true
                           :npm-deps {:create-react-class "15.7.0"
                                      ;; :react "17.0.1"
                                      ;; :react-dom "17.0.1"
                                      :react-pageflip "1.0.0"}}}]}
                                      ;; :webpack "5.3.1"
                                      ;; :webpack-cli "4.1.0"}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             :server-port 3000;; default
             ;; :server-ip "127.0.0.1"


             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this

             ;; doesn't work for you just run your own server :) (see lein-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             ;; to pipe all the output to the repl
             ;; :server-logfile false
             }
  ;; :npm {:dependencies [[react-pageflip "1.0.0"]]}

  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.0"]
                                  [figwheel-sidecar "0.5.20"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; need to add the compiled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
