(ns allentiak.scramble.rest-api.specs)

(def ^:private scramble-parameters
  [:map
   [:letters
    {:title "letters parameter"
     :description "description for letters parameter"
     :json-schema/default "abc"}
    [:re #"([a-zA-Z]+)|(^\s*$)"]]
   [:word
    {:title "word parameter"
     :description "description for word parameter"
     :json-schema/default "abc"}
    [:re #"([a-zA-Z]+)|(^\s*$)"]]])

(def scramble-parameters--get
  {:query
   scramble-parameters})

(def scramble-parameters--post
  {:body
   scramble-parameters})

(def scramble-response
  {200
   {:body [:map [:scramble? boolean?]]}})
