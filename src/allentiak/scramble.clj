(ns allentiak.scramble
  (:gen-class)
  (:require
   [allentiak.scramble.backend :as backend]
   [clojure.string :as str]
   [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-l" "--letters LETTERS" "Letters"
    :required "Must be provided"
    :validate [#(not (str/blank?)) "Must not be empty"]]
   ["-w" "--word WORD" "Word"
    :required "Must be provided"
    :validate [#(not (str/blank?)) "Must not be empty"]]])

(defn -main
  "Callable entry point of the application."
  [& args]
  (apply backend/scramble? (parse-opts args cli-options)))
