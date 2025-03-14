import { Badge } from "../ui/Badge";

function StockBadge({ stock }: { stock: number }) {
    let color: "default" | "secondary" | "destructive" | "success";
    let label: string;
  
    if (stock === 0) {
      color = "destructive";
      label = "Out of Stock";
    } else if (stock > 10) {
      color = "success";
      label = "In Stock";
    } else {
      color = "secondary";
      label = "Low Stock";
    }
  
    return <Badge color={color}>{label}</Badge>;
  }

  export default StockBadge;
  