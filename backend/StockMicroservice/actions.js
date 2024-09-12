// actions.js
import client from './redisClient';

export const BEGIN_TRANSACTION = 'BEGIN_TRANSACTION';
export const COMMIT_TRANSACTION = 'COMMIT_TRANSACTION';
export const ROLLBACK_TRANSACTION = 'ROLLBACK_TRANSACTION';

export const beginTransaction = () => async (dispatch) => {
  // Persist transaction start in Redis
  await client.set('transaction:status', 'started');
  dispatch({ type: BEGIN_TRANSACTION });
};

export const commitTransaction = () => async (dispatch) => {
  // Persist transaction commit in Redis
  await client.set('transaction:status', 'committed');
  dispatch({ type: COMMIT_TRANSACTION });
};

export const rollbackTransaction = () => async (dispatch) => {
  // Persist transaction rollback in Redis
  await client.set('transaction:status', 'rolledback');
  dispatch({ type: ROLLBACK_TRANSACTION });
};
