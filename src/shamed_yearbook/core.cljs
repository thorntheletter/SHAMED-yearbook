(ns shamed_yearbook.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [react-pageflip :as pf]
            [goog.string :as gstring]))

(enable-console-print!)

(def home [:h1 "Hello Wrold"])
(println (type pf))
 (.-innerWidth js/window) (.-innerHeight js/window)

(def book-ratio (/ 11 17))

(def flip-book (r/adapt-react-class pf))

(def testpage [:div.page {:style {:margin-left 10 :background-color "black" :text-align "left" :background-image "/images/yearbook/yearbook001.png" :height "300"}} "besto"])

(defn max-page-size
  [width height]
  (if (> (/ height width ) book-ratio) ;; this will probably become more complex as they tell me about footer and once i figure out how to swap to single page layout
    {:width (str (/ width 2)) :height (str (* width book-ratio))} ;; taller than book at max width; height depends on screen width
    {:width (str (/ height book-ratio 2)) :height (str height)})) ;; wider than book at max height; width depends on screen height

(def page-size (r/atom (max-page-size (.-innerWidth js/window) (.-innerHeight js/window))))

(defn page ;; i have absolutely no idea why it wont respect the style tags but its really annoying; perhaps see if it is the same in normal js
  [number]
  [:div.page {:key number :class (str "page_" number)} [:img {:src (gstring/format "/images/yearbook2/%03d.png" number)}]])

(defn test-book []
  ;; [:div#book {:style {:width (* (@page-size :width) 2)}}
   [:div#book {:width "1224px"}
   ;; [:div (str @page-size)]
   (println @page-size)
   ;; [flip-book (merge {
   ;;                    ; :width "100px" :height "100"
   ;;                    :min-width "10" :min-height "300"
   ;;                    ;; :max-width "10000" :max-height "10000"
   ;;                    :size "fixed" ;:auto-size true
   ;;                    :showCover true :flippingTime "250"}
   ;;                   @page-size)
    [flip-book {:width "612" :height "792" :showCover true :flippingTime "250"}
    ;; (for [i (range 1 33)] ^{:key i} (page2 i))]])
    (for [i (range 1 17)] ^{:key i} (page i))]])

;; (defn max-page-size2
;;   [width height]
;;   (if (> (/ height width ) book-ratio) ;; this will probably become more complex as they tell me about footer and once i figure out how to swap to single page layout
;;     {:width (str (/ width 2) "px") :height (str (* width book-ratio) "px")} ;; taller than book at max width; height depends on screen width
;;     {:width (str (/ height book-ratio 2) "px") :height (str height "px")})) ;; wider than book at max height; width depends on screen height

(defn on-window-resize [event]
  (reset! page-size (max-page-size (.-innerWidth js/window) (.-innerHeight js/window)))
  (println "resized!" @page-size))
  ;; (set! (.-innerHTML (.getElementById js/document "app")) "aaa")
  ;; (rd/render test-book (.getElementById js/document "app")))

;; (println (.-innerWidth js/window) (.-innerHeight js/window))
;; (println (max-page-size (.-innerWidth js/window) (.-innerHeight js/window)))

(defn ^:export main []
  (rd/render test-book (.getElementById js/document "app"))
  (.addEventListener js/window "resize" on-window-resize))

(main)
;; (rd/render [home]
;;                 (.getElementById js/document "app"))

(defonce app-state (atom {:text "Hello world!!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
