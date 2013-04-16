(ns common
  (require [clojure.string :as str]))

(defn id [x] x)

(defn trace [x] (prn x) x)

(defn sum [xs]
  (reduce + 0 xs))

(defn product [& xs]
  (reduce * 1 (flatten xs)))

(defn split [string]
  (filter (comp not str/blank?)
          (str/split string #"")))

(defn max-by [f xs]
  (reduce (fn [acc x]
            (if (> (f acc) (f x))
              acc
              x))
          (first xs)
          (rest xs)))

(defn slurp-lines [file]
  (str/split-lines (str/trim-newline (slurp file))))

(defn third [xs]
  (nth xs 2))
(defn fourth [xs]
  (nth xs 3))

(defn char-range [from to]
  (map (comp str char) (range (int from) (int to))))
