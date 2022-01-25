(ns clojure-challenges.scramble-web-server
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes web-server-routes
  (GET "/" [] "It works!")
  (route/not-found "This isn't the page you're looking for."))

(def web-server
  (wrap-defaults web-server-routes api-defaults))
