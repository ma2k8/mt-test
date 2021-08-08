Rails.application.routes.draw do
  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  Rails.application.routes.draw do
    namespace 'api' do
      namespace 'v1' do
        resources :users, :users_reactor, :users_thread
      end
    end
  end
  
end
