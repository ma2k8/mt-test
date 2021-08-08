require 'benchmark'

module Api
    module V1
      class UsersController < ApplicationController
        before_action :set_post, only: [:show, :update, :destroy]
  
        def index
          res = Benchmark.bm 10 do |r|
            r.report "N+1 find db" do
              users = (1..500).map {|i| User.find(i) }
              filtered = users.filter { |v| v.id.modulo(2) == 0 }
              render json: { status: 'SUCCESS', message: 'Loaded users', data: filtered }
            end
          end
          p res  
        end
  
      end
    end
  end
