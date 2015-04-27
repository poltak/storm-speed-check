package edu.monash.honours.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

public class SpeedCheckBolt extends BaseRichBolt
{
  private final static int SPEED_UPPER_BOUND = 90;
  private final static int SPEED_LOWER_LIMIT = 0;

  private static int totalTupleCount = 0;
  private int             tupleCount;
  private OutputCollector collector;

  /**
   * Runs once when this bolt is created.
   */
  public void prepare(final Map map, final TopologyContext topologyContext, final OutputCollector outputCollector)
  {
    this.collector = outputCollector;
    this.tupleCount = 0;
  }

  /**
   * Runs every time a tuple is received.
   *
   * @param tuple The input tuple containing a single speed field.
   */
  public void execute(final Tuple tuple)
  {
    updateTupleCounts();

    Object receivedValue = tuple.getValue(0);

    Values emitTuple;
    if (receivedValue instanceof Double) {
      // If received value is a double, check the speed received
      emitTuple = checkSpeed((Double) receivedValue);
    }
    else {
      // else, ignore processing and send the message onwards
      emitTuple = new Values(receivedValue, true);
    }

    this.collector.emit(tuple, emitTuple);
    this.collector.ack(tuple);
  }

  /**
   * Declare the output fields as being speed and a noise flag.
   */
  public void declareOutputFields(final OutputFieldsDeclarer outputFieldsDeclarer)
  {
    outputFieldsDeclarer.declare(new Fields("speed", "noiseFlag"));
  }

  /**
   * Keeps count of tuples that pass through this bolt.
   */
  private void updateTupleCounts()
  {
    this.tupleCount++;
    totalTupleCount += this.tupleCount;
  }

  /**
   * Checks the given speed to see if it within limits.
   *
   * @param speed Speed to check.
   * @return A Values tuple with two fields: speed and a noise flag. Noise flag is checked if speed exceeds limits.
   */
  private Values checkSpeed(double speed)
  {
    if (speed > SPEED_UPPER_BOUND || speed < SPEED_LOWER_LIMIT) {
      return new Values(speed, true);
    }

    return new Values(speed, false);
  }


}
