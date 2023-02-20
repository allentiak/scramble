(ns allentiak.scramble
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str]))

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
  (parse-opts args cli-options))
