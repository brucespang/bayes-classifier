(ns classifier
  (use [common]
       [clojure.algo.generic.functor])
  (require [clojure.string :as str]))

;; We concatenate the alphabet to the input words, to prevent overfitting due to
;; missing letters
(def ^:private alphabet (char-range \A \Z))

(defn- map-uniform-probability [m]
  (let [total (sum (map count (vals m)))]
    (fmap #(/ (count %) total)
          m)))

(defn- compute-priors [corpus]
  (map-uniform-probability corpus))

(defn- compute-letter-likelihoods [cities]
  (let [words (concat cities alphabet)
        letter-occurances (group-by id
                                    (mapcat (comp distinct split)
                                            words))]
    (map-uniform-probability letter-occurances)))

(defn- compute-likelihoods [corpus]
  (fmap compute-letter-likelihoods corpus))

(defn- letter-probabilities [word model]
  (map (fn [letter]
         [letter (fmap #(get % letter 0)
                       (:likelihoods model))])
       (split word)))

(defn- joint-probabilities [letter-probabilities model]
  (apply hash-map
         (mapcat (fn [class]
                   [class (product (map (fn [[_ probabilities]]
                                          (get probabilities class))
                                        letter-probabilities)
                                   (get (:priors model) class))])
                 (:classes model))))

(defn train [corpus]
  {:priors (compute-priors corpus)
   :likelihoods (compute-likelihoods corpus)
   :classes (keys corpus)})

(defn classify [string model]
  (let [letter-probs (letter-probabilities string model)
        joint-probs (joint-probabilities letter-probs model)
        denom (sum (vals joint-probs))
        probs (map (fn [[class joint-prob]]
                             {:city string
                              :map-class class
                              :probability (/ joint-prob denom)})
                           joint-probs)]
    (max-by :probability
            probs)))
