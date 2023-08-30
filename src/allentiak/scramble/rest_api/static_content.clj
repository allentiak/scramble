(ns allentiak.scramble.rest-api.static-content
  (:require
   [hiccup.page :as p]
   [hiccup.form :as f]))

(defn homepage
  []
  (p/html5
   [:head
    [:title "Simple form to test my API"]]
   [:body
    [:h1 "Testing scramble API..."]
    (f/form-to [:post "/scramble"]
               [:div
                [:label {:for "letters"} "Letters:"]
                [:input {:type "text" :id "letters" :name "letters"}]]
               [:div
                [:label {:for "word"} "Word:"]
                [:input {:type "text" :id "word" :name "word"}]]
               [:div
                [:input {:type "submit" :value "Submit"}]])]))

(comment
  (doc f/text-field))
