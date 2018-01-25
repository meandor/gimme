(ns com.github.meandor.redis-component-test
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [taoensso.carmine :as car]
            [com.github.meandor.redis-component :as rc]
            [clojure.tools.logging :as log])
  (:import (redis.embedded RedisServer)))

(defrecord Configuring []
  component/Lifecycle
  (start [self]
    (log/info "-> loading configuration.")
    (assoc self :config {:default-redis {:pool {} :spec {}}}))
  (stop [self]
    (log/info "<- stopping configuration.")
    self))

(defn new-config []
  (map->Configuring {}))

(defn- base-system []
  (component/system-map
    :config (component/using (new-config) [])
    :redis (component/using (rc/new-redis-component "default") [:config])))

(defn- start [system]
  (log/info "-> Starting system.")
  (let [started (component/start system)]
    (log/info "-> System completely started.")
    started))

(defn- stop [system]
  (log/info "<- Stopping system.")
  (component/stop system))

(defn with-db-fixture [f]
  (let [db (RedisServer.)]
    (.start db)
    (f)
    (.stop db)))

(use-fixtures :once with-db-fixture)

(deftest start-component-test
  (let [system (start (base-system))]
    (testing "start and stop of component with given config component"
      (is (= :default
             (:id (:redis system))))

      (is (= {:pool {} :spec {}}
             (:connection (:redis system)))))

    (testing "flush db, read empty value, set new value, read value from redis db with component"
      (rc/wcar* (:redis system) (car/flushall))
      (is (nil? (rc/wcar* (:redis system) (car/get "foo"))))

      (rc/wcar* (:redis system) (car/set "foo" "bar"))
      (is (= "bar"
             (rc/wcar* (:redis system) (car/get "foo")))))
    (stop system)))
