(ns classifier
  (use [common])
  (require [clojure.string :as str]))

;; We concatenate the alphabet to the input words, to prevent overfitting due to
;; missing letters
(def alphabet (map (comp str char) (range (int \A) (int \Z))))

(defn compute-priors [corpus]
  (let [total (map-sum-count corpus)]
    (map-map (fn [[class words]]
               [class (/ (count words) total)])
             corpus)))

(defn compute-letter-likelihoods [cities]
  (let [words (concat cities alphabet)
        letter-occurances (group-by id
                                    (mapcat (comp distinct split)
                                            words))
        total (map-sum-count letter-occurances)]
    (map-map (fn [[letter occurances]]
               [letter (/ (count occurances) total)])
             letter-occurances)))

(defn compute-likelihoods [corpus]
  (map-map (fn [[class words]]
             [class (compute-letter-likelihoods words)])
           corpus))

(defn train [corpus]
  {:priors (compute-priors corpus)
   :likelihoods (compute-likelihoods corpus)
   :classes (map first corpus)})

(defn word-probabilities [word model]
  (map (fn [letter]
         [letter (map-map (fn [[class letter-likelihoods]]
                            [class (or (letter-likelihoods letter) 0)])
                          (:likelihoods model))])
       (split word)))

(defn joint-probabilities [word-probabilities model]
  (map (fn [class]
         [class (product (map (fn [[letter probabilities]]
                                (get probabilities class))
                              word-probabilities)
                         (get (:priors model) class))])
       (:classes model)))

(defn classify [string model]
  (let [word-probabilities (word-probabilities string model)
        joint-probabilities (joint-probabilities word-probabilities model)
        denominator (sum (map second joint-probabilities))
        probabilities (map (fn [[class x]]
                             {:city string
                              :map-class class
                              :probability (/ x denominator)})
                           joint-probabilities)]
    (max-by :probability
            probabilities)))
