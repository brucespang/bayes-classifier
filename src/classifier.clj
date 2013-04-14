(ns classifier
  (use [common])
  (require [clojure.string :as str]))

(def alphabet (map (comp str char) (range (int \A) (int \Z))))

(defn compute-priors [words]
  (let [corpus (concat words alphabet)
        total (map-total corpus)]
    (apply hash-map
           (mapcat (fn [[class words]]
                     [class (/ (count words) total)])
                   corpus))))

(defn compute-letter-likelihoods [cities]
  (let [words (concat cities alphabet)
        letter-occurances (group-by id
                                    (mapcat (comp distinct split)
                                            words))
        total (map-total letter-occurances)]
    (apply hash-map
           (mapcat (fn [[letter occurances]]
                     [letter (/ (count occurances) total)])
                   letter-occurances))))

(defn compute-likelihoods [corpus]
  (apply hash-map
         (mapcat (fn [[class words]]
                   [class (compute-letter-likelihoods words)])
                 corpus)))

(defn train [corpus]
  (let [priors (compute-priors corpus)
        likelihoods (compute-likelihoods corpus)]
    {:priors priors :likelihoods likelihoods :classes (map first corpus)}))

(defn word-probabilities [word model]
  (map (fn [letter]
         [letter
          (apply hash-map
                 (mapcat (fn [[class letter-likelihoods]]
                           [class (or (letter-likelihoods letter) 0)])
                         (:likelihoods model)))])
       (split word)))

(defn class-probabilities [word-probabilities model]
  (map (fn [class]
         [class (float (* (product
                           (map (fn [[letter probabilities]]
                                  (probabilities class))
                                word-probabilities))
                          ((:priors model) class)))])
       (:classes model)))

(defn classify [string model]
  (let [word-probabilities (word-probabilities string model)
        class-probabilities (class-probabilities word-probabilities model)
        denominator (sum (map second class-probabilities))
        probabilities (map (fn [[class x]]
                             {:city string
                              :map-class class
                              :probability (/ x denominator)})
                           class-probabilities)]
    (max-by :probability
            probabilities)))
