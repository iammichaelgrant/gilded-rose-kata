(ns gilded-rose.core)

(defn item [item-name, sell-in, quality]
  {:name item-name, :sell-in sell-in, :quality quality})

(def inventory
  [(item "+5 Dexterity Vest" 10 20)
   (item "Aged Brie" 2 0)
   (item "Elixir of the Mongoose" 5 7)
   (item "Sulfuras, Hand Of Ragnaros" 0 80)
   (item "Backstage passes to a TAFKAL80ETC concert" 15 20)])

(defn aged-brie? [item] (= (:name item) "Aged Brie"))
(defn sulfuras? [item] (= (:name item) "Sulfuras, Hand of Ragnaros"))
(defn backstage-pass? [item] (clojure.string/starts-with? (:name item) "Backstage pass"))
(defn conjured? [item] (clojure.string/starts-with? (:name item) "Conjured"))

(defn update-quality [quality increase amount]
  (if increase
    (min 50 (+ quality amount))
    (max 0 (- quality amount))))

(defn update-aged-brie [item]
  (-> item
      (update :quality #(update-quality % true 1))
      (update :sell-in dec)))

(defn update-backstage-pass [item]
  (-> item
      (update :quality #(cond
                          (< 10 (:sell-in item)) (update-quality % true 1)
                          (< 5 (:sell-in item)) (update-quality % true 2)
                          (< 0 (:sell-in item)) (update-quality % true 3)
                          :else 0))
      (update :sell-in dec)))

(defn update-sulfuras [item]
  item)

(defn update-conjured [item]
  (-> item
      (update :quality #(if (neg? (:sell-in item))
                          (update-quality % false 4)
                          (update-quality % false 2)))
      (update :sell-in dec)))

(defn update-normal [item]
  (-> item
      (update :quality #(if (neg? (:sell-in item))
                          (update-quality % false 2)
                          (update-quality % false 1)))
      (update :sell-in dec)))

(defn update-item [item]
  (cond
    (aged-brie? item) (update-aged-brie item)
    (sulfuras? item) (update-sulfuras item)
    (backstage-pass? item) (update-backstage-pass item)
    (conjured? item) (update-conjured item)
    :else (update-normal item)))

(defn update-inventory [items]
  (map update-item items))

(defn update-current-inventory[inventory days]
  (loop [days days
         inventory inventory]
    (if-not (pos? days)
      inventory
      (recur (dec days) (update-inventory inventory)))))
