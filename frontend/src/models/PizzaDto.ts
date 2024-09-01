export interface PizzaDto {
  customerName: string,
  pizzaSize: string,
  deliveryAddress: string,
  status: string,
  emailAddress: string,
  pizzaName: string,
  extraIngredients: string[],
  price: number;
}