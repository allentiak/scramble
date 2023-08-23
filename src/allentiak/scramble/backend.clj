(ns allentiak.scramble.backend
  (:require
    [clojure.string :as str]))


(defn- rearrangable?
  "Returns true if a portion of letters can be rearranged to match word, otherwise
  returns false.

  Worst possible case scenario comparison. Only considers lowercase a-z characters."
  [letters word]
  (cond
    (nil? word) true
    (not (str/includes? letters (str (first word)))) false
    :else (rearrangable? letters (seq (rest word)))))

(comment
  (rearrangable? "ab" "b"))


(defn scramble?
  "Returns true if a portion of letters can be rearranged to match word, otherwise
  returns false.

  Efficient version. Only considers lowercase a-z characters."
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
