(ns clojure-challenges.scramble-web-server
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn default-page []
  "Just a RESTful interface for 'scramble?'")

(defroutes web-server-routes
  (GET "/" [] (default-page))
  (route/not-found "This isn't the page you're looking for."))

(def web-server
  (wrap-defaults web-server-routes api-defaults))

(def dev-web-server
  (wrap-reload #'web-server))

;; it won't work unless I use the dash
(defn -main [port]
  (jetty/run-jetty
   dev-web-server
   {:port (Integer. port)}))
