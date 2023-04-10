(ns allentiak.scramble.rest-api
  (:require
    [allentiak.scramble.backend :as backend]
    [hiccup.page :as h]
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

(def plain-routes
  [["/" {:summary "Show a landing page"
         :get basic-response}]])

(def webapp
  (reitit-ring/ring-handler
   (reitit-ring/router
    plain-routes)
   (reitit-ring/create-default-handler)))
