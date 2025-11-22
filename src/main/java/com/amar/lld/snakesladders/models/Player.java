package com.amar.lld.snakesladders.models;

public class Player {
	private String name;
	private String id;
    private PlayerState playerState;

	public Player(String name, String id) {
		this.name = name;
		this.id = id;
        this.playerState = PlayerState.NONE;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

    public PlayerState getPlayerState(){
        return playerState;
    }

    public void setPlayerState(PlayerState state){
         playerState = state;
    }
}
