package burlap.domain.singleagent.lunarlander;

import burlap.mdp.core.oo.OODomain;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.GroundedAction;
import burlap.mdp.singleagent.RewardFunction;

/**
 * A reward function for the {@link burlap.domain.singleagent.lunarlander.LunarLanderDomain}. By default, this
 * reward function returns -1 except when the agent hits the ground, hits an obstacle, hits the side of the landing pad,
 * or lands on the landing
 * pad. When the agent hits the ground, obstacle, or side of the landing pad it receives -100. When it lands on the landing pad,
 * it receives +1000. The rewards for these conditions may also be modified.
 * @author James MacGlashan.
 */
public class LunarLanderRF implements RewardFunction{

	/**
	 * The reward for landing on the landing pad
	 */
	public double							goalReward = 1000.0;

	/**
	 * The reward for hitting the ground or an obstacle
	 */
	public double							collisionReward = -100.0;

	/**
	 * The default reward received for moving through the air
	 */
	public double							defaultReward = -1.0;

	protected PropositionalFunction 		onGround;
	protected PropositionalFunction			touchingSurface;
	protected PropositionalFunction			touchingPad;
	protected PropositionalFunction			onPad;


	/**
	 * Initializes with default reward values (move through air = -1; collision = -100; land on pad = +1000)
	 * @param domain a {@link burlap.domain.singleagent.lunarlander.LunarLanderDomain} generated {@link burlap.mdp.core.Domain}.
	 */
	public LunarLanderRF(OODomain domain){
		this.onGround = domain.getPropFunction(LunarLanderDomain.PF_ON_GROUND);
		this.touchingSurface = domain.getPropFunction(LunarLanderDomain.PF_TOUCH_SURFACE);
		this.touchingPad = domain.getPropFunction(LunarLanderDomain.PF_TOUTCH_PAD);
		this.onPad = domain.getPropFunction(LunarLanderDomain.PF_ON_PAD);
	}



	/**
	 * Initializes with custom reward condition values.
	 * @param domain a {@link burlap.domain.singleagent.lunarlander.LunarLanderDomain} generated {@link burlap.mdp.core.Domain}.
	 * @param goalReward the reward for landing on the landing pad.
	 * @param collisionReward the reward for a collision.
	 * @param defaultReward the default reward for all other states (i.e., moving through the air)
	 */
	public LunarLanderRF(OODomain domain, double goalReward, double collisionReward, double defaultReward){
		this(domain);
		this.goalReward = goalReward;
		this.collisionReward = collisionReward;
		this.defaultReward = defaultReward;
	}

	@Override
	public double reward(State s, GroundedAction a, State sprime) {
		if(onPad.somePFGroundingIsTrue((OOState)sprime)){
			return goalReward;
		}

		if(this.onGround.somePFGroundingIsTrue((OOState)sprime) || this.touchingPad.somePFGroundingIsTrue((OOState)sprime) || this.touchingSurface.somePFGroundingIsTrue((OOState)sprime)){
			return collisionReward;
		}

		return defaultReward;
	}


	public double getGoalReward() {
		return goalReward;
	}

	public void setGoalReward(double goalReward) {
		this.goalReward = goalReward;
	}

	public double getCollisionReward() {
		return collisionReward;
	}

	public void setCollisionReward(double collisionReward) {
		this.collisionReward = collisionReward;
	}

	public double getDefaultReward() {
		return defaultReward;
	}

	public void setDefaultReward(double defaultReward) {
		this.defaultReward = defaultReward;
	}
}
