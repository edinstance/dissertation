import { Item } from "@/gql/graphql";
import { PencilSquareIcon } from "@heroicons/react/24/outline";
import { Button } from "../ui/Button";
import { Card, CardContent, CardFooter } from "../ui/Card";
import { Badge } from "../ui/Badge";

function ItemOverviewGrid({ items }: { items: Item[] }) {
  return (
    <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
      {items.map((item) => (
        <Card
          key={item.id}
          className="group relative overflow-hidden transition-shadow hover:shadow-lg flex flex-col"
        >
          <CardContent className="p-4 flex-grow">
            <div className="mb-2 flex items-start justify-between">
              <h3 className="group-hover:text-primary text-xl font-semibold transition-colors">
                {item.name}
              </h3>
              <div className="hidden md:block">
                {item.stock && <StockBadge stock={item?.stock ?? 0} />}
              </div>
            </div>
            <p className="text-muted-foreground mb-4 text-sm">
              {item.description}
            </p>
            <p className="text-sm font-medium">Stock: {item.stock}</p>
          </CardContent>
          <CardFooter className="flex justify-end p-4 pt-0">
            <Button variant="outline" href={`/items/createItem?itemId=${item.id}`}>
              <PencilSquareIcon className="mr-2 h-4 w-4" />
              Edit
            </Button>
          </CardFooter>
        </Card>
      ))}
    </div>
  );
}

export default ItemOverviewGrid;

function StockBadge({ stock }: { stock: number }) {
  let variant: "default" | "secondary" | "destructive";
  let label: string;

  if (stock > 10) {
    variant = "default";
    label = "In Stock";
  } else if (stock > 0) {
    variant = "secondary";
    label = "Low Stock";
  } else {
    variant = "destructive";
    label = "Out of Stock";
  }

  return <Badge variant={variant}>{label}</Badge>;
}
