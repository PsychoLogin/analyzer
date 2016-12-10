package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.Writable;

import java.util.List;

/**
 * Created by othma on 10.12.2016.
 */
public interface IWritableConvertible {
    /**
     * @brief
     *
     * @return
     */
    List<Writable> toWritable();
}
