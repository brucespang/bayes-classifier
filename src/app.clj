(ns app
  (:gen-class)
  (use [clojure.tools.cli :only [cli]]
       [common]
       [classifier])
  (require [clojure.string :as str]))

(defn classify-all [class model]
  (map (fn [city] (classify city model))
       (:eval class)))

(defn compute-accuracy [results actual-class]
  (let [classifications (map :map-class results)
        correct-classifications (filter (fn [class] (= class actual-class))
                                        classifications)]
    (/ (count correct-classifications)
       (count classifications))))

(defn run-classifier [class model]
  (let [results (classify-all class model)
        accuracy (compute-accuracy results (:name class))]
    (str/join "\n" (concat
                    [(format "%s" (:name class))
                     (format "accuracy: %.2f"  (float accuracy))
                     "city,map-class,probability"]
                    (map (fn [res]
                           (format "%s,%s,%.4f"
                                   (:city res)
                                   (:map-class res)
                                   (float (:probability res)))                     )
                         results)))))

(defn load-class [class data]
  (let [key (fn [suffix] (keyword (str (name class) "-" suffix)))]
    {:name     (get data (key "name"))
     :training (slurp-lines (get data (key "training")))
     :eval     (slurp-lines (get data (key "eval")))}))

(defn -main [& args]
  (let [input (cli args
                   ["-fn" "--first-name" "Name of the first class"]
                   ["-ft" "--first-training" "Training data for first class"]
                   ["-fe" "--first-eval" "Evaluation data for first class"]
                   ["-sn" "--second-name" "Name of the second class"]
                   ["-st" "--second-training" "Training data for second class"]
                   ["-se" "--second-eval" "Evaluation data for second class"])]
    (if (not (empty? (first input)))
      (let [data (first input)
            first (load-class :first data)
            second (load-class :second data)
            model (train {(:name first) (:training first)
                          (:name second) (:training second)})]
        (println (run-classifier first model))
        (println "")
        (println (run-classifier second model)))
      (print (third input))))
  nil)