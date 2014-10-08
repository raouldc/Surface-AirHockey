using Microsoft.Xna.Framework;
using Microsoft.Surface.Core;


namespace AirHockey
{
    public enum PlayerNumber
    {
        Player1 = 1,
        Player2 = 2
    }

    /// <summary>
    /// This is a game component that implements IUpdateable.
    /// </summary>
    public class Player : BaseGamePiece
    {
        public PlayerNumber _playerNumber;
        private PlayerTouchBinder _touchBinder;
        private TouchTarget touchTarget;

        public Player(Game game, PlayerNumber playerNumber, PlayerTouchBinder touchBinder)
            : base(game)
        {
            _playerNumber = playerNumber;
            _touchBinder = touchBinder;
        }

        public Player(Game airHockeyGame, PlayerNumber playerNumber, PlayerTouchBinder binder, TouchTarget touchTarget)
            : base(airHockeyGame)
        {
            // TODO: Complete member initialization
            _playerNumber = playerNumber;
            _touchBinder = binder;
            this.touchTarget = touchTarget;
        }

        public override float Mass { get { return 10; } }

        /// <summary>
        /// Allows the game component to perform any initialization it needs to before starting
        /// to run.  This is where it can query for any required services and load content.
        /// </summary>
        public override void Initialize()
        {
            base.Initialize();
        }

        protected override void LoadContent()
        {
            switch (_playerNumber)
            {
                case PlayerNumber.Player1:
                    LoadTexture("Player1");
                    break;

                case PlayerNumber.Player2:
                    LoadTexture("Player2");
                    break;

                default:
                    break;
            }

            base.LoadContent();
        }

        public override void Update(GameTime gameTime)
        {
            HandlePlayerInput();

            base.Update(gameTime);
        }

        private void HandlePlayerInput()
        {
            ReadOnlyTouchPointCollection touchCollection = touchTarget.GetState();
            //TouchCollection touchCollection = TouchPanel.GetState();

            foreach (TouchPoint touchLoc in touchCollection)
            {

                //test code

                // Check that the touch point is not already bound to a player.
                if (!_touchBinder.IsTouchPointBound(touchLoc.Id))
                {
                    // Identify if this is the player that owns the area where this touch point has made contact.
                    if ((touchLoc.X < 400 && _playerNumber == PlayerNumber.Player1) || (touchLoc.X > 400 && _playerNumber == PlayerNumber.Player2))
                    {
                        // Bind the touch location to this player so in the future this touch point always controls this player.
                        _touchBinder.Bind(touchLoc.Id, _playerNumber);
                    }
                }
                if (touchLoc.IsFingerRecognized || InteractiveSurface.PrimarySurfaceDevice.IsFingerRecognitionSupported == false)
                {
                    if (_touchBinder.IsTouchPointBoundToPlayer(touchLoc.Id, _playerNumber))
                    {
                        Vector2 newVelocity = new Vector2(touchLoc.X, touchLoc.Y) - Position;
                        newVelocity = newVelocity * 0.01f;

                        newVelocity = RestrictMaxPlayerVelocity(newVelocity);
                        SetVelocity(newVelocity);
                    }
                }

                //if (_touchBinder.IsTouchPointBoundToPlayer(touchLoc.Id, _playerNumber))
                //{
                //    if (touchLoc.State == TouchLocationState.Released)
                //    {
                //        // If the touch point is released we need to unbind it from the player it was bound to.
                //        _touchBinder.Release(touchLoc.Id);
                //    }
                //    else if (touchLoc.State == TouchLocationState.Moved)
                //    {
                //        // Provided the touch point is bound to this player.
                //        // Calculate the distance between the player and the touch
                //        // location and apply velocity to the player towards the touch point.
                //        Vector2 newVelocity = touchLoc.Position - Position;
                //        newVelocity = newVelocity * 0.01f;

                //        newVelocity = RestrictMaxPlayerVelocity(newVelocity);
                //        SetVelocity(newVelocity);
                //    }
                //}
                //    else if (touchLoc.State == TouchLocationState.Pressed || touchLoc.State == TouchLocationState.Moved)
                //    {
                //        // Check that the touch point is not already bound to a player.
                //        if (!_touchBinder.IsTouchPointBound(touchLoc.Id))
                //        {
                //            // Identify if this is the player that owns the area where this touch point has made contact.
                //            if ((touchLoc.Position.X < 400 && _playerNumber == PlayerNumber.Player1) || (touchLoc.Position.X > 400 && _playerNumber == PlayerNumber.Player2))
                //            {
                //                // Bind the touch location to this player so in the future this touch point always controls this player.
                //                _touchBinder.Bind(touchLoc.Id, _playerNumber);
                //            }
                //        }
                //    }
                //}

                //int count = 0;
                //foreach (TouchPoint touch in touches)
                //{
                //    // Create a sprite for each touch that has been recognized as a finger, 
                //    // or for any touch if finger recognition is not supported.
                //    if (touch.IsFingerRecognized || InteractiveSurface.PrimarySurfaceDevice.IsFingerRecognitionSupported == false)
                //    {
                //        SpriteData sprite = new SpriteData(new Vector2(touch.X, touch.Y),
                //            touch.Orientation,
                //            1.0f);
                //        sprites.AddLast(sprite); // always add to the end
                //        count++;
                //    }
                //}
                //return count;
            }
        }

        private Vector2 RestrictMaxPlayerVelocity(Vector2 playerVelocity)
        {
            if (playerVelocity.Length() > 1)
            {
                playerVelocity.Normalize();
                return playerVelocity;
            }
            else
            {
                return playerVelocity;
            }
        }
        public override string ToString()
        {
            return _playerNumber.ToString();
        }

    }
}
