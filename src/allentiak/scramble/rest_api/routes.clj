(ns allentiak.scramble.rest-api.routes
  (:require
   [allentiak.scramble.rest-api.handlers :as handlers]
   [allentiak.scramble.rest-api.specs :as specs]))

(def scramble-api-route
  ["/scramble"
   {:tags ["scramble"]
    :get
    {:summary "scramble with query parameters"
     :parameters specs/scramble-parameters--get
     :responses specs/scramble-response
     :handler handlers/scramble-get-handler
     :openapi
     {:requestQuery
      {:content
       {"application/json"
        handlers/scramble-examples--request--json-content-map}}
      :responses
      handlers/scramble-examples--response-200--json-content-map}}
    :post
    {:summary "scramble with body parameters"
     :parameters specs/scramble-parameters--post
     :responses specs/scramble-response
     :handler handlers/scramble-post-handler
     :openapi
     {:requestBody
      {:content
       {"application/json"
        handlers/scramble-examples--request--json-content-map}}
      :responses
      handlers/scramble-examples--response-200--json-content-map}}}])
