(ns allentiak.scramble.rest-api.handlers
  (:require
   [allentiak.scramble.backend :as backend]))

(defn- scramble-handler [letters word]
  {:status 200
   :body
   {:scramble?
    (backend/scramble? letters word)}})

(defn scramble-get-handler
  [{{{:keys [letters word]} :query} :parameters}]
  (scramble-handler letters word))

(defn scramble-post-handler
  [{{{:keys [letters word]} :body} :parameters}]
  (scramble-handler letters word))

(def ^:private scramble-parameters--malli-schema
  [:map
   [:letters
    {:title "letters parameter"
     :description "description for letters parameter"
     :json-schema/default "abc"}
    [:re #"([a-zA-Z]+)|(^\s*$)"]]
   [:word
    {:title "word parameter"
     :description "description for word parameter"
     :json-schema/default "abc"}
    [:re #"([a-zA-Z]+)|(^\s*$)"]]])

(def scramble-parameters--get
  {:query
   scramble-parameters--malli-schema})

(def scramble-parameters--post
  {:body
   scramble-parameters--malli-schema})
