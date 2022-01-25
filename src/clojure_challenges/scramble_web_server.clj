(ns clojure-challenges.scramble-web-server
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn default-page []
  "Just a RESTful interface for 'scramble?'")

(defroutes web-server-routes
  (GET "/" [] (default-page))
  (route/not-found "This isn't the page you're looking for."))

(def web-server
  (wrap-defaults web-server-routes api-defaults))
