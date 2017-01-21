package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.Writable;

import java.util.List;

/**
 * Interface for nn entities and datasets
 */
public interface IWritableConvertible {
    List<Writable> toWritable();
}
