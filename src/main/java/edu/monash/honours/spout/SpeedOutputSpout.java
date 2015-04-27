package edu.monash.honours.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Map;

public class SpeedOutputSpout extends BaseRichSpout
{
  private static final String LISTENING_PORT_CONFIG_KEY = "spout.listeningPort";

  private SpoutOutputCollector collector;
  private ServerSocket         serverSocket;

  /**
   * Runs once when this spout is created.
   *
   * @param conf Storm topology configuration map.
   * @param topologyContext
   * @param spoutOutputCollector
   */
  public void open(final Map conf, final TopologyContext topologyContext,
                   final SpoutOutputCollector spoutOutputCollector)
  {
    this.collector = spoutOutputCollector;

    try {
      this.serverSocket = new ServerSocket((int) conf.get(LISTENING_PORT_CONFIG_KEY));
    } catch (IOException e) {
      this.collector.emit(new Values("ERROR: Cannot open port -\n" + e.getMessage()));
    }
  }

  /**
   * Run every time a new value is sent to this spout.
   * <p>
   * Emits a tuple containing a single speed value for further processing.
   */
  public void nextTuple()
  {
    try {
      BufferedReader bufferedReader = new BufferedReader(
        (new InputStreamReader(serverSocket.accept().getInputStream())));
      double speedReading = Double.valueOf(bufferedReader.readLine());

      this.collector.emit(new Values(speedReading));
    } catch (IOException e) {
      this.collector.emit(new Values("ERROR: Cannot read from port -\n" + e.getMessage()));
    }
  }

  /**
   * Declare the output field as being a single speed value.
   */
  public void declareOutputFields(final OutputFieldsDeclarer outputFieldsDeclarer)
  {
    outputFieldsDeclarer.declare(new Fields("speed"));
  }
}
