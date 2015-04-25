package edu.monash.honours.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

public class SpeedOutputSpout extends BaseRichSpout
{
  private SpoutOutputCollector collector;
  private ServerSocket         serverSocket;

  /**
   * Runs once when this spout is created.
   *
   * @param map
   * @param topologyContext
   * @param spoutOutputCollector
   */
  public void open(final Map map, final TopologyContext topologyContext,
                   final SpoutOutputCollector spoutOutputCollector)
  {
    this.collector = spoutOutputCollector;

    try {
      this.serverSocket = new ServerSocket(9000);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Run every time a new value is sent to this spout.
   * <p>
   * Emits a tuple containing a single speed value for further processing.
   */
  public void nextTuple()
  {
    double speed = 0;

    Values emitTuple = new Values(speed);
    this.collector.emit(emitTuple);
  }

  /**
   * Declare the output field as being a single speed value.
   */
  public void declareOutputFields(final OutputFieldsDeclarer outputFieldsDeclarer)
  {
    outputFieldsDeclarer.declare(new Fields("speed"));
  }
}
