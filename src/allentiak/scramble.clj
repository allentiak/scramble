(ns allentiak.scramble
  (:gen-class)
  (:require
   [clojure.string :as str]
   [clojure.pprint :as pprint]
   [clojure.tools.cli :refer [parse-opts]]
   [allentiak.scramble.backend :as backend]))

(def cli-options
  [["-l" "--letters LETTERS" "Letters"
    :validate [#(not (str/blank? %)) "Must not be empty"]]
   ["-w" "--word WORD" "Word"
    :validate [#(not (str/blank? %)) "Must not be empty"]]])

(defn usage [options-summary]
  (->> ["This is scramble's CLI."
        ""
        "Usage: scramble [options]"
        ""
        "Options:"
        options-summary
        ""
        "Please refer to the manual page for more information."]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(comment
  (let [options {:letters "arb" :word "ab"}]
    (count options)
    (keys options)
    (= #{:letters :word} (set (keys options)))))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "Callable entry point of the application."
  [& args]
  (let [{:keys [options exit-message ok?] :as full-map} (parse-opts args cli-options)]
    (println "Parsed options:")
    (pprint/pprint (parse-opts args cli-options))
    (println "Full map:")
    (pprint/pprint full-map)
    (println "Args:")
    (pprint/pprint args)
    (println "End of last printing.")
    (if exit-message
      (do
        (println "Something went wrong...")
        (exit (if ok? 0 1) exit-message))
      (do
        (println "Everything went fine!")
        (println
         (backend/scramble? (:letters options) (:word options)))))))

(comment
  (-main "-l ab -w ab"))
