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
    :validate [#(not (str/blank? %)) "Must not be empty"]]
   ["-h" "--help"]])

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

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with an error message, and optional ok status), or a map
  indicating the values for the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)                   ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}

      errors                         ; errors => exit with description of errors
      {:exit-message (error-msg errors)}

      :else                 ; everything went fine => return the options' values
      {:options options})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "Callable entry point of the application."
  [& args]
  (let [{:keys [options exit-message ok?] :as full-map} (validate-args args)]
    (println "***DEBUGGING - Start***")
    (println "Parsed options:")
    (pprint/pprint (parse-opts args cli-options))
    (println "Full map:")
    (pprint/pprint full-map)
    (println "Args:")
    (pprint/pprint args)
    (println "***DEBUGGING - End***")
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
