(ns pickado.table)

(defn weight [range]
  (let [hit (rand)]
    (count (filter #(<= % hit) range))))

(defn field [] (keyword (str (inc (rand-int 20)))))

(defn center? [table] (< (rand) (:center-to-field-ratio table) ))

(defn throw-dart [table]
  (if (center? table)
    {:center (weight (:center-area table))}
    {(field) (weight (:field-area table))}))

(defn round [table]
  (repeatedly 3 #(throw-dart table)))
