alter table restaurants add column is_on_wolt boolean;
update restaurant_app.public.restaurants SET is_on_wolt = FALSE;
