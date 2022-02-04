(ns clojure-challenges.server.scramble-webserver
  (:require
   [clojure-challenges.apis.scramble :as scramble]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
   [ring.middleware.reload :refer [wrap-reload]]))

(defn default-page-handler [req]
  "Just a RESTful interface for 'scramble?'")

(defn- scramble-handler
  [letters word]
  (str (scramble/scramble? letters word)))

(defroutes webserver-routes
  (GET "/" req default-page-handler)
  (POST "/scramble" [letters word] (scramble-handler letters word))
  (route/not-found "This isn't the page you're looking for."))

(def webserver
  (-> webserver-routes
      (wrap-defaults api-defaults)))

(def dev-webserver
  (-> #'webserver
      (wrap-reload)))

;; it won't work unless I use the dash
(defn -main [port]
  (jetty/run-jetty
   dev-webserver
   {:port (Integer. port)}))
