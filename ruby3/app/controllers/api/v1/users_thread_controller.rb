require 'parallel'

module Api
  module V1
    class UsersThreadController < ApplicationController
      before_action :set_post, only: [:show, :update, :destroy]

      def index

        users = []
        Parallel.each(1..500, in_threads: 5) do |i|
          users.push(User.find(i))
        end

        filtered = []
        Parallel.each(users, in_threads: 5) do |u|
          if (u.id.modulo(2) == 0) then filtered.push(u)
          else filtered
          end
        end

        render json: { status: 'SUCCESS', message: 'Loaded users', data: filtered }
      end

    end
  end
end
