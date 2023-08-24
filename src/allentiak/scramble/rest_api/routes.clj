(ns allentiak.scramble.rest-api.routes
  (:require
   [allentiak.scramble.rest-api.handlers :as handlers]))

(def scramble-route
  ["/scramble"
   {:tags ["scramble"]
    :get
    {:summary "scramble with query parameters"
     :parameters handlers/scramble-parameters--get
     :responses handlers/scramble-response--malli-schema
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
     :parameters handlers/scramble-parameters--post
     :responses handlers/scramble-response--malli-schema
     :handler handlers/scramble-post-handler
     :openapi
     {:requestBody
      {:content
       {"application/json"
        handlers/scramble-examples--request--json-content-map}}
      :responses
      handlers/scramble-examples--response-200--json-content-map}}}])
