(ns com.github.meandor.redis-component
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log]
            [taoensso.carmine :as car]))

(defmacro wcar* [rc & body]
  `(car/wcar ~(:connection-config rc) ~@body))

(defn- redis-config [id config]
  (->> (str (name id) "-redis")
       (keyword)
       (get config)))

(defrecord RedisComponent [id config]
  component/Lifecycle
  (start [self]
    (log/infof "-> starting %s RedisComponent" (name id))
    (assoc self :connection (redis-config id (:config config))))
  (stop [_]
    (log/infof "<- stopping %s RedisComponent" (name id))))

(defn new-redis-component [config-prefix]
  (map->RedisComponent {:id (keyword config-prefix)}))
