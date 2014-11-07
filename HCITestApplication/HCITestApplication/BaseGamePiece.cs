using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using CollisionLib;
using Microsoft.Xna.Framework.Graphics;

namespace AirHockey
{
    public abstract class BaseGamePiece : DrawableGameComponent, ICollidableCircle
    {
        private Texture2D _texture;
        private Vector2 _position;
        private Vector2 _velocity;
        private float _scale = 3f;
        private Vector2 _friction;

        private SpriteBatch _spriteBatch;

        private AirHockeyGame _game;
        private String name;

        public abstract float Mass { get; }

        public BaseGamePiece(Game game)
            : base(game)
        {
            _game = (AirHockeyGame)game;
        }

        public override void Initialize()
        {
            _friction = new Vector2(0.005f, 0.005f);

            base.Initialize();
        }

        protected override void LoadContent()
        {
            _spriteBatch = new SpriteBatch(GraphicsDevice);

            base.LoadContent();
        }

        protected void LoadTexture(String textureName)
        {
            name = textureName;
            _texture = this.Game.Content.Load<Texture2D>(textureName);
        }

        /// <summary>
        /// Allows the game component to update itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        public override void Update(GameTime gameTime)
        {
            HandleMovement(gameTime);

            base.Update(gameTime);
        }

        private void HandleMovement(GameTime gameTime)
        {
            _position.X = (float)(_position.X + (_velocity.X * gameTime.ElapsedGameTime.TotalMilliseconds));
            _position.Y = (float)(_position.Y + (_velocity.Y * gameTime.ElapsedGameTime.TotalMilliseconds));

            //_player2Position.X = (float)(_player2Position.X + (_player2Velocity.X * gameTime.ElapsedGameTime.TotalMilliseconds));
            //_player2Position.Y = (float)(_player2Position.Y + (_player2Velocity.Y * gameTime.ElapsedGameTime.TotalMilliseconds));
        }

        public override void Draw(GameTime gameTime)
        {
            DrawPeice();

            base.Draw(gameTime);
        }

        private void DrawPeice()
        {
            _spriteBatch.Begin();

            if (name.Equals("Puck"))
            {
                _spriteBatch.Draw(_texture, _position, null, Color.White, 0, new Vector2(_texture.Width / 2, _texture.Height / 2), 1.5f, SpriteEffects.None, 0);
            }
            else
            {
                _spriteBatch.Draw(_texture, _position, null, Color.White * 0.2f, 0, new Vector2(_texture.Width / 2, _texture.Height / 2), _scale, SpriteEffects.None, 0);
            }
            _spriteBatch.End();
        }

        public float Diameter
        {
            get
            {
                if (name.Equals("Puck"))
                {
                    return _texture.Width;
                }
                return _texture.Width * 3f;
            }
        }

        public float Radius { get; private set; }

        public void SetPosition(Vector2 newPosition)
        {
            SetState(newPosition, _velocity);
        }

        public void SetVelocity(Vector2 newVelocity)
        {
            SetState(Position, newVelocity);
        }

        public void SetState(Vector2 position, Vector2 velocity)
        {
            _position = position;
            _velocity = velocity;
        }

        public Vector2 Position
        {
            get { return _position; }
        }

        public Vector2 Velocity
        {
            get { return _velocity; }
        }
    }
}
