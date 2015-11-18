package com.ibm.cds.spark.samples.dstream

import scala.collection.JavaConversions._
import scala.collection.Map
import scala.reflect.ClassTag
import scala.reflect.classTag
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.Deserializer
import org.apache.spark.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming.receiver.Receiver
import org.apache.log4j.Level
import org.apache.log4j.Logger
import java.util.Properties
import com.ibm.cds.spark.samples.StatusDeserializer
import com.ibm.cds.spark.samples.config.MessageHubConfig

class KafkaInputDStream[
  K: ClassTag,
  V: ClassTag,
  U <: Deserializer[_]: ClassTag,
  T <: Deserializer[_]: ClassTag](
    ssc : StreamingContext,
    kafkaParams: Map[String, String],
    topics: List[String],
    storageLevel: StorageLevel = StorageLevel.MEMORY_AND_DISK
  ) extends ReceiverInputDStream[(K, V)](ssc) with Logging {

  def getReceiver(): Receiver[(K, V)] = {
      new KafkaReceiver[K, V, U, T](kafkaParams, topics, storageLevel)
  }
}

object KafkaStreaming{
  //Logger.getLogger("org.apache.kafka").setLevel(Level.ALL)
  //Logger.getLogger("kafka").setLevel(Level.ALL)
  implicit class KafkaStreamingContextAdapter( val ssc : StreamingContext ){
    def createKafkaStream[K: ClassTag, V: ClassTag, U <: Deserializer[_]: ClassTag, T <: Deserializer[_]: ClassTag](
      topics: List[String]
    ): ReceiverInputDStream[(K, V)] = {
      val kafkaProps = new MessageHubConfig;
      kafkaProps.setValueDeserializer[StatusDeserializer];
      new KafkaInputDStream[K, V, U, T](ssc, kafkaProps.toImmutableMap, topics)
    }
  }
}

class KafkaReceiver[
  K: ClassTag,
  V: ClassTag,
  U <: Deserializer[_]: ClassTag,
  T <: Deserializer[_]: ClassTag](
    kafkaParams: Map[String,String],
    topics: List[String],
    storageLevel: StorageLevel
  ) extends Receiver[(K, V)](storageLevel) with Logging {

  // Connection to Kafka
  var kafkaConsumer: KafkaConsumer[K,V] = null

  def onStop() {
    if (kafkaConsumer != null) {
      this.synchronized {
        print("Stopping kafkaConsumer")
        kafkaConsumer.close()
        kafkaConsumer = null
      }
    }
  }

  def onStart() {
    logInfo("Starting Kafka Consumer Stream")
    
    val keyDeserializer = classTag[U].runtimeClass.getConstructor().newInstance().asInstanceOf[Deserializer[K]]
    val valueDeserializer = classTag[T].runtimeClass.getConstructor().newInstance().asInstanceOf[Deserializer[V]]
    
    kafkaConsumer = new KafkaConsumer[K, V](kafkaParams)
    kafkaConsumer.subscribe( topics )
    
    new Thread( new Runnable {
      def run(){
        try{
			    while( kafkaConsumer != null ){
            Thread.sleep( 1000L )
            var it:Iterator[ConsumerRecord[K, V]] = null;
            this.synchronized{
              if ( kafkaConsumer != null ){
                it = kafkaConsumer.poll(1000L).iterator
              }
            }
            while( it != null && it.hasNext() ){
              val record = it.next();
              store( (record.key, record.value) )
            }
          }  
          println("Exiting Thread")
        }catch{
          case e:Throwable => e.printStackTrace()
        }
	    }
    }).start
  }
}
