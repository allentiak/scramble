(ns allentiak.scramble.rest-api.examples)

(def request
  {:examples
   {"scramble?-abc-a"
    {:summary "abc-a"
     :value {:letters "abc"
             :word "a"}}
    "scramble?-a-abc"
    {:summary "a-abc"
     :value {:letters "a"
             :word "abc"}}}})

(def response-200
  {200
   {:content
    {"application/json"
     {:examples
      {"true"
       {:summary "true"
        :value {:scramble? true}}
       "false"
       {:summary "false"
        :value {:scramble? false}}}}}}})
