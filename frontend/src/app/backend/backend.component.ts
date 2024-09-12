import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { StockWebSocketService } from '@services/StockWebSocketService';
import { WebSocketService } from '@services/WebSocketService';
import { environment } from 'environments/environment';
import { IngredientDto } from 'models/IngerdientDto';
import { Utils } from 'utils/utils';


@Component({
    selector: 'app-backend',
    templateUrl: './backend.component.html',
    styleUrls: ['./backend.component.css']
})
export class BackendComponent {
    pizzaIngredients: any;

    constructor(
        private http: HttpClient,
        private webSocketService: WebSocketService,
        private stockWebSocketService: StockWebSocketService) { }

    orders: any[] = [];

    ngOnInit(): void {
        this.pizzaIngredients = Utils.availablePizzaIngredients();

        this.stockWebSocketService.getUpdates().subscribe(update => {
            if(Array.isArray(update)){
                update.forEach(stockUpdate => {
                    const index = this.pizzaIngredients.findIndex((ingredient: IngredientDto) => ingredient.value === stockUpdate.name);
                    if (index !== -1) {
                        this.pizzaIngredients[index].quantity = Number(stockUpdate.quantity);
                    }
                });
            }
            else{
                let ingredientUpdate = update['ingredient'];
                const index = this.pizzaIngredients.findIndex((ingredient: IngredientDto) => ingredient.value === ingredientUpdate.name);
                if (index !== -1) {
                    this.pizzaIngredients[index].quantity = Number(ingredientUpdate.quantity);
                }
            }
        });

        this.webSocketService.getUpdates().subscribe(update => {
            try{
                const index = this.orders.findIndex(order => order.id === update.id);
                if (index !== -1) {
                    this.orders[index] = update;
                } else {
                    this.orders.push(update);
                }
            }
            catch(e){
                console.error('Error:', e);
            }
        });
    }

    updateOrderStatus(id: string, newStatus: string) {
        this.http.post<any>(`${environment.apiUrl}/order/orders/${id}`, newStatus)
            .subscribe(
                data => {
                    alert("Order status updated successfully!");
                },
                error => {
                    console.error('Error:', error);
                }
            );
    }

    restockIngredient = (event: MouseEvent, value: string) => {
        let ingredient = this.pizzaIngredients.find((ingredient: { value: string, name: string }) => ingredient.value === value) || null;
        if (!!ingredient) {
            this.http.post<any>(`${environment.apiStockUrl}/stock/restock/${ingredient.value}`, {})
                .subscribe(
                    data => {
                        alert("Ingredient stock updated successfully!");
                    },
                    error => {
                        console.error('Error:', error);
                        // Handle errors here
                    }
                );
        }
    }
}
