(ns allentiak.scramble.rest-api
  (:require
    [allentiak.scramble.backend :as backend]
    [hiccup.page :as h]
    [reitit.core :as r]
    [reitit.ring :as reitit-ring]))


(defn landing-page
  []
  (h/html5
    [:head
     [:title "Landing page"]]
    [:body "Server running..."]))


(defn basic-response
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (landing-page)})


(defn routes
  []
  [["/" {:summary "Show a landing page"
         :get basic-response}]])


(def webapp
  (reitit-ring/ring-handler
    (reitit-ring/router
      (routes))
    (reitit-ring/create-default-handler)))


(def dev-router #(r/router (routes)))
(def prod-router (constantly (r/router (routes))))
