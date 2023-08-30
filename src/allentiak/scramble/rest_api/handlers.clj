(ns allentiak.scramble.rest-api.handlers
  (:require
   [allentiak.scramble.backend :as backend]
   [allentiak.scramble.rest-api.static-content :as static-content]))

(defn- scramble-wrapper [letters word]
  {:status 200
   :body
   {:scramble?
    (backend/scramble? letters word)}})

(defn scramble-get-handler
  [{{{:keys [letters word]} :query} :parameters}]
  (scramble-wrapper letters word))

(defn scramble-post-handler
  [{{{:keys [letters word]} :body} :parameters}]
  (scramble-wrapper letters word))

(defn homepage-handler []
  (constantly {:status 200
               :body (static-content/homepage)}))
