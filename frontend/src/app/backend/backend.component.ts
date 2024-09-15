import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { StockWebSocketService } from '@services/StockWebSocketService';
import { WebSocketService } from '@services/WebSocketService';
import { environment } from 'environments/environment';
import { IngredientDto } from 'models/IngerdientDto';
import { Utils } from 'utils/utils';
import { ToastrService } from 'ngx-toastr';


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
        private stockWebSocketService: StockWebSocketService,
        private toastr: ToastrService) { }

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
                    if(update.status === "PLACED")
                        this.toastr.warning('', `A new order has been placed by ${update.customerName}`);
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
                    this.toastr.success('Order status updated successfully!',"", {positionClass: 'toast-bottom-right'});
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
                        this.toastr.success(`Added ${value} of ${value} to stock successfully!`, "", {positionClass: 'toast-bottom-right'});
                    },
                    error => {
                        console.error('Error:', error);
                        // Handle errors here
                    }
                );
        }
    }
}
