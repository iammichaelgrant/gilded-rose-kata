(ns gilded-rose.core-spec
(:require [clojure.test :refer :all]
          [gilded-rose.core :refer :all]))

(deftest normal-item

  (are [input days expected]
    (= expected (update-current-inventory input days))
    [(item "+5 Dexterity Vest" 10 20)] 1 [(item "+5 Dexterity Vest" 9 19)]
    [(item "+5 Dexterity Vest" 1 20)] 1 [(item "+5 Dexterity Vest" 0 19)]
    [(item "+5 Dexterity Vest" 0 20)] 1 [(item "+5 Dexterity Vest" -1 19)]
    [(item "+5 Dexterity Vest" -1 20)] 1 [(item "+5 Dexterity Vest" -2 18)]

    [(item "+5 Dexterity Vest" 10 1)] 1 [(item "+5 Dexterity Vest" 9 0)]
    [(item "+5 Dexterity Vest" 10 0)] 1 [(item "+5 Dexterity Vest" 9 0)]
    [(item "+5 Dexterity Vest" 10 0)] 2 [(item "+5 Dexterity Vest" 8 0)]))

(deftest aged-brie
  (are [input days expected]
    (= expected (update-current-inventory input days))
    [(item "Aged Brie" 2 0)] 1 [(item "Aged Brie" 1 1)]
    [(item "Aged Brie" 2 0)] 2 [(item "Aged Brie" 0 2)]
    [(item "Aged Brie" 2 0)] 3 [(item "Aged Brie" -1 3)]
    [(item "Aged Brie" 2 0)] 23 [(item "Aged Brie" -21 23)]
    [(item "Aged Brie" 2 0)] 223 [(item "Aged Brie" -221 50)]))

(deftest sulfuras
  (are [input days expected]
    (= expected (update-current-inventory input days))
    [(item "Sulfuras, Hand of Ragnaros" 1 80)] 1 [(item "Sulfuras, Hand of Ragnaros" 1 80)]
    [(item "Sulfuras, Hand of Ragnaros" 0 80)] 1 [(item "Sulfuras, Hand of Ragnaros" 0 80)]
    [(item "Sulfuras, Hand of Ragnaros" 0 80)] 2 [(item "Sulfuras, Hand of Ragnaros" 0 80)]
    [(item "Sulfuras, Hand of Ragnaros" 0 80)] 200 [(item "Sulfuras, Hand of Ragnaros" 0 80)]))

(deftest backstage-passes
  (are [input days expected]
    (= expected (update-current-inventory input days))
    [(item "Backstage passes to a TAFKAL80ETC concert" 15 1)] 5 [(item "Backstage passes to a TAFKAL80ETC concert" 10 6)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 10 1)] 5 [(item "Backstage passes to a TAFKAL80ETC concert" 5 11)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 5 1)] 5 [(item "Backstage passes to a TAFKAL80ETC concert" 0 16)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 5 1)] 6 [(item "Backstage passes to a TAFKAL80ETC concert" -1 0)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 5 1)] 7 [(item "Backstage passes to a TAFKAL80ETC concert" -2 0)]

    [(item "Backstage passes to a TAFKAL80ETC concert" 15 49)] 1 [(item "Backstage passes to a TAFKAL80ETC concert" 14 50)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 15 49)] 2 [(item "Backstage passes to a TAFKAL80ETC concert" 13 50)]
    [(item "Backstage passes to a TAFKAL80ETC concert" 15 40)] 14 [(item "Backstage passes to a TAFKAL80ETC concert" 1 50)]))

(deftest conjured-items
  (are [input days expected]
    (= expected (update-current-inventory input days))
    [(item "Conjured bread" 10 20)] 1 [(item "Conjured bread" 9 18)]
    [(item "Conjured bread" 1 20)] 1 [(item "Conjured bread" 0 18)]
    [(item "Conjured bread" 0 20)] 1 [(item "Conjured bread" -1 18)]
    [(item "Conjured bread" -1 20)] 1 [(item "Conjured bread" -2 16)]
    [(item "Conjured bread" -1 20)] 2 [(item "Conjured bread" -3 12)]

    [(item "Conjured bread" 10 2)] 1 [(item "Conjured bread" 9 0)]
    [(item "Conjured bread" 10 1)] 1 [(item "Conjured bread" 9 0)]
    [(item "Conjured bread" 10 0)] 1 [(item "Conjured bread" 9 0)]
    [(item "Conjured bread" 10 0)] 2 [(item "Conjured bread" 8 0)]))
