(ns allentiak.scramble
  (:gen-class)
  (:require
    [clojure.string :as str]
    [clojure.pprint :as pprint]
    [clojure.tools.cli :refer [parse-opts]]
    [allentiak.scramble.backend :as backend]))

(def cli-options
  [["-l" "--letters LETTERS" "Letters"
    :required "Must be provided"
    :validate [#(not (str/blank? %)) "Must not be empty"]]
   ["-w" "--word WORD" "Word"
    :required "Must be provided"
    :validate [#(not (str/blank? %)) "Must not be empty"]]])

(defn -main
  "Callable entry point of the application."
  [& args]
  (pprint/pprint (parse-opts args cli-options))
  (apply backend/scramble? (parse-opts args cli-options)))
