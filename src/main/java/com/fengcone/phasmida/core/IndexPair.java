package com.fengcone.phasmida.core;

/**
 * @author fengcone
 *
 */
public class IndexPair {
	private int startIndex;
	private int endIndex;
	private int fragmentsIndex;

	public int getFragmentsIndex() {
		return fragmentsIndex;
	}

	public void setFragmentsIndex(int fragmentsIndex) {
		this.fragmentsIndex = fragmentsIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	@Override
	public String toString() {
		return "IndexPair [startIndex=" + startIndex + ", endIndex=" + endIndex + ", fragmentsIndex=" + fragmentsIndex + "]";
	}

}
