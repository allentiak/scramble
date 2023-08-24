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

(def scramble-examples--request--json-content-map
  {:examples
   {"scramble?-abc-a"
    {:summary "abc-a"
     :value {:letters "abc"
             :word "a"}}
    "scramble?-a-abc"
    {:summary "a-abc"
     :value {:letters "a"
             :word "abc"}}}})

(def scramble-examples--response-200--json-content-map
  {200
   {:content
    {"application/json"
     {:examples
      {"true"
       {:summary "true"
        :value {:scramble? true}}
       "false"
       {:summary "false"
        :value {:scramble? false}}}}}}})
