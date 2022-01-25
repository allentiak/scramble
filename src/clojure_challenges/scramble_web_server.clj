(ns clojure-challenges.scramble-web-server
  (:require [clojure-challenges.scramble :as scramble]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn default-page []
  "Just a RESTful interface for 'scramble?'")

(defroutes web-server-routes
  (GET "/" [] (default-page))
  (POST "/scramble" [letters word] (str (scramble/scramble? letters word)))
  (route/not-found "This isn't the page you're looking for."))

(def web-server
  (-> web-server-routes
      (wrap-defaults api-defaults)))

(def dev-web-server
  (-> #'web-server
      (wrap-reload)))

;; it won't work unless I use the dash
(defn -main [port]
  (jetty/run-jetty
   dev-web-server
   {:port (Integer. port)}))
