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
