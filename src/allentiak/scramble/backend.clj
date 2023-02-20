(ns allentiak.scramble.backend
  (:gen-class)
  (:require
    [clojure.string :as str]))


(defn- rearrangable?
  "returns true if a portion of letters can be rearranged to match word, otherwise returns false.
  Worst possible case scenario comparison.
  Only considers lowercase a-z characters."
  [letters word]
  (cond
    (nil? word) true
    (not (str/includes? letters (str (first word)))) false
    :else (rearrangable? letters (seq (rest word)))))

(comment
  (rearrangable? "ab" "b"))


(defn scramble?
  "returns true if a portion of letters can be rearranged to match word, otherwise returns false.
  Efficient version.
  Only considers lowercase a-z characters."
  [letters word]
  (cond
    (or (nil? letters)
        (nil? word)
        (< (count letters) (count word)))
    false

    (or (= letters word)
        (str/includes? letters word))
    true

    :else
    (rearrangable? letters word)))


(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))
